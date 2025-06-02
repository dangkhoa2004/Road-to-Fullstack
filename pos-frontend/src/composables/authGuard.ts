import type { NavigationGuardNext, RouteLocationNormalized } from 'vue-router'

export function authGuard(
    to: RouteLocationNormalized,
    from: RouteLocationNormalized,
    next: NavigationGuardNext
) {
    const token = localStorage.getItem('jwtToken')
    const user = localStorage.getItem('user')

    const publicRoutes = [
        '/dang-nhap',
        '/dang-ky',
        '/quen-mat-khau',
        '/error-404',
    ]

    const isPublicRoute =
        to.meta.public || publicRoutes.includes(to.path)

    if ((!token || !user) && !isPublicRoute) {
        const redirectPath =
            to.fullPath && to.fullPath !== '/' ? to.fullPath : undefined

        next({
            path: '/dang-nhap',
            query: redirectPath ? { redirect: redirectPath } : undefined,
        })
    } else {
        next()
    }
}
