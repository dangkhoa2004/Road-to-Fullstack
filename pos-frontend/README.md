# TailAdmin Vue - Phần Của Dự Án “ROAD-TO-FULLSTACK: Hành Trình Chinh Phục Lập Trình Fullstack Từ Zero Đến Hero 🚀”

TailAdmin Vue là một **mảnh ghép** trong dự án **ROAD-TO-FULLSTACK**, một tài liệu sống phản ánh toàn bộ hành trình tự
học và thực hành của tôi trong lĩnh vực lập trình Fullstack. Đây không chỉ là một **admin dashboard template** mạnh mẽ,
mà còn là **minh chứng** cho khả năng áp dụng lý thuyết vào thực tiễn, cũng như quá trình học hỏi, cải thiện và làm chủ
công nghệ.

TailAdmin Vue là **một dự án con** dựa trên **Vue.js 3** – một framework JavaScript hiện đại – kết hợp cùng **Tailwind
CSS** để xây dựng một hệ thống quản trị (admin panel) mạnh mẽ, dễ tùy biến và tối ưu cho các ứng dụng web.

![TailAdmin Vue.js Dashboard Preview](./banner.png)

## Giới Thiệu

TailAdmin Vue được xây dựng trên các công nghệ tiên tiến:

* **Vue 3 (Vite)**: Framework hiệu suất cao với kiến trúc component-based và khả năng reactive tuyệt vời.
* **TypeScript**: Mang lại sự an toàn và dễ bảo trì cho dự án.
* **Tailwind CSS**: Framework CSS tiện lợi giúp phát triển UI nhanh chóng và chuẩn responsive.

Mục tiêu là cung cấp **một điểm khởi đầu chất lượng** cho việc xây dựng các dashboard dữ liệu phong phú, phù hợp với nhu
cầu của bất kỳ dự án web nào.

## Các Liên Kết Nhanh

* [✨ Trang chủ](https://tailadmin.com)
* [📄 Tài liệu hướng dẫn](https://tailadmin.com/docs)
* [⬇️ Tải xuống](https://tailadmin.com/download)
* [🖌️ Figma Design File (Community Edition)](https://www.figma.com/community/file/1463141366275764364)
* [⚡ Phiên bản PRO](https://tailadmin.com/pricing)

### Demos

* [Free Version](https://free-vue-demo.tailadmin.com/)
* [Pro Version](https://vue-demo.tailadmin.com)

### Các Phiên Bản Khác

* [HTML Version](https://github.com/TailAdmin/tailadmin-free-tailwind-dashboard-template)
* [Next.js Version](https://github.com/TailAdmin/free-nextjs-admin-dashboard)
* [React Version](https://github.com/TailAdmin/free-react-tailwind-admin-dashboard)

## Cài Đặt

### Yêu Cầu

Trước khi bắt đầu, đảm bảo bạn đã cài đặt:

* **Node.js 18.x hoặc mới hơn** (Khuyến nghị: Node.js 20.x)
* IDE: **[VSCode](https://code.visualstudio.com/)** + *
  *[Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar)** (và tắt Vetur)

**Lưu ý:** TypeScript không xử lý tốt file `.vue` mặc định – cần dùng `vue-tsc` và cài đặt Volar để có hỗ trợ TypeScript
tốt hơn.

### Các Bước

1️⃣ Clone repository:

```bash
git clone https://github.com/TailAdmin/vue-tailwind-admin-dashboard.git
```

2️⃣ Cài đặt dependencies:

```bash
npm install
# hoặc
yarn install
```

3️⃣ Chạy dev server:

```bash
npm run dev
# hoặc
yarn dev
```

4️⃣ Build production:

```bash
npm run build
# hoặc
yarn build
```

## So Sánh Phiên Bản

| Tính năng            | Free Version | Pro Version                                           |
|----------------------|--------------|-------------------------------------------------------|
| Dashboard độc đáo    | 1            | 5 (Analytics, Ecommerce, CRM, Marketing, Stocks, ...) |
| Thành phần dashboard | 30+          | 400+                                                  |
| UI Elements          | 50+          | 400+                                                  |
| Figma design         | Cơ bản       | Hoàn chỉnh                                            |
| Hỗ trợ               | Community    | Email                                                 |

👉 Xem chi tiết: [Pricing Page](https://tailadmin.com/pricing)

## Các Thành Phần Nổi Bật

✅ Sidebar đẹp mắt và dễ truy cập
✅ Các biểu đồ (Line, Bar) với ApexCharts
✅ Trang quản lý hồ sơ và trang 404 mẫu sẵn
✅ Hỗ trợ Dark Mode 🕶️
✅ Modal, Dropdown, Alerts, Tables, Forms...

Mọi thành phần đều được xây dựng **với Vue.js** và **Tailwind CSS**, dễ dàng tùy biến và mở rộng.

## Tính Năng Nổi Bật

* **💎 Thiết kế hiện đại, UX/UI cao cấp:** Được hơn 10K+ ứng dụng web tin tưởng sử dụng.
* **⚡ Vite build system:** Giúp quá trình phát triển nhanh và mượt mà.
* **🔀 Vue Router 4:** Quản lý điều hướng hiệu quả.
* **🗺️ JSVectorMap:** Thêm các bản đồ vector trực quan.
* **💡 Reactive utilities (VueUse):** Tăng cường tính phản ứng của các thành phần.
* **💫 TypeScript Support:** Code sạch, dễ bảo trì.
* **🗃️ Pinia:** Quản lý state rõ ràng và dễ mở rộng.

## Nhật Ký Cập Nhật

### v2.0.1 - \[27/02/2025]

* Nâng cấp lên Tailwind CSS v4
* Tối ưu class, cập nhật cú pháp
* Chạy `npm install` để đồng bộ dependencies mới.

### v2.0.0 - \[02/2025]

* **Major update:** Chuyển sang Vue 3, nâng cấp giao diện.
* Thêm dashboard mới (Ecommerce, CRM, ...)
* Sidebar collapsible, real-time chat, drag-and-drop calendar...

[Chi tiết tại đây](https://tailadmin.com/docs/update-logs/vue)

### v1.0.2 - \[19/06/2024]

* Fix hamburger icon trên mobile

### v1.0.1 - \[08/02/2024]

* Cập nhật Multiselect Dropdown
* Tái cấu trúc SelectGroup

### v1.0.0 - Initial Release - \[22/01/2024]

---

**Lưu ý:** Đây chỉ là một phần của **ROAD-TO-FULLSTACK**, tập hợp toàn bộ những bài học, kỹ năng và nỗ lực của tôi trên
hành trình làm chủ lập trình fullstack. Hãy cùng khám phá, học hỏi và phát triển! 🚀