@echo off
setlocal enableDelayedExpansion
rem Đặt mã hóa UTF-8 để hiển thị tiếng Việt đúng cách
chcp 65001 > nul

:MAIN_MENU
cls
echo ===================================================
echo       CHƯƠNG TRÌNH QUẢN LÝ DỰ ÁN
echo ===================================================
echo.
echo   1. Mở file README.md
echo   2. Xem cấu trúc thư mục dự án (TREE)
echo   3. Cập nhật mã nguồn (Git Pull)
echo   4. Khởi chạy Dự án (Backend và Frontend)
echo   5. Thoát khỏi chương trình
echo.
echo ===================================================
set /p choice=" >> Xin mời chọn một lựa chọn (1-5): "

if /i "%choice%"=="1" goto OPEN_README
if /i "%choice%"=="2" goto VIEW_TREE_MENU
if /i "%choice%"=="3" goto GIT_PULL
if /i "%choice%"=="4" goto START_PROJECT
if /i "%choice%"=="5" goto END
echo.
echo ^>_<^ Lựa chọn của bạn không hợp lệ. Vui lòng thử lại!
pause > nul
goto MAIN_MENU

:OPEN_README
cls
echo ===================================================
echo       ĐANG MỞ FILE README.md...
echo ===================================================
echo.
rem Kiểm tra xem file README.md có tồn tại không
if exist "README.md" (
  start "" "README.md"
  echo ^>^> File README.md đã được mở thành công.
) else (
  echo ^>_<^ Xin lỗi, không tìm thấy file "README.md" trong thư mục hiện tại.
  echo    Vui lòng đảm bảo file README.md nằm cùng cấp với script này.
)
echo.
echo ===================================================
echo Nhấn phím bất kỳ để quay lại menu chính...
pause > nul
goto MAIN_MENU

:VIEW_TREE_MENU
cls
echo ===================================================
echo     MENU XEM CẤU TRÚC THƯ MỤC (TREE)
echo ===================================================
echo.
echo   1. Xem toàn bộ cấu trúc thư mục dự án
echo   2. Xem cấu trúc thư mục "pos-backend\src"
echo   3. Xem cấu trúc thư mục "pos-frontend\src"
echo   4. Quay lại Menu Chính
echo.
echo ===================================================
set /p tree_choice=" >> Xin mời chọn một lựa chọn (1-4): "

if /i "%tree_choice%"=="1" goto SHOW_FULL_TREE
if /i "%tree_choice%"=="2" goto SHOW_BACKEND_SRC_TREE
if /i "%tree_choice%"=="3" goto SHOW_FRONTEND_SRC_TREE
if /i "%tree_choice%"=="4" goto MAIN_MENU
echo.
echo ^>_<^ Lựa chọn của bạn không hợp lệ. Vui lòng thử lại!
pause > nul
goto VIEW_TREE_MENU

:SHOW_FULL_TREE
cls
echo ===================================================
echo     HIỂN THỊ TOÀN BỘ CẤU TRÚC DỰ ÁN
echo ===================================================
echo.
tree /F /A
echo.
echo ===================================================
echo Nhấn phím bất kỳ để quay lại menu trước...
pause > nul
goto VIEW_TREE_MENU

:SHOW_BACKEND_SRC_TREE
cls
echo ===================================================
echo   HIỂN THỊ CẤU TRÚC THƯ MỤC "POS-BACKEND\SRC"
echo ===================================================
echo.
if exist "pos-backend\src" (
  pushd "pos-backend\src"
  tree /F /A
  popd
) else (
  echo ^>_<^ Xin lỗi, thư mục "pos-backend\src" không tồn tại.
  echo    Vui lòng kiểm tra lại đường dẫn và cấu trúc thư mục.
)
echo.
echo ===================================================
echo Nhấn phím bất kỳ để quay lại menu trước...
pause > nul
goto VIEW_TREE_MENU

:SHOW_FRONTEND_SRC_TREE
cls
echo ===================================================
echo   HIỂN THỊ CẤU TRÚC THƯ MỤC "POS-FRONTEND\SRC"
echo ===================================================
echo.
if exist "pos-frontend\src" (
  pushd "pos-frontend\src"
  tree /F /A
  popd
) else (
  echo ^>_<^ Xin lỗi, thư mục "pos-frontend\src" không tồn tại.
  echo    Vui lòng kiểm tra lại đường dẫn và cấu trúc thư mục.
)
echo.
echo ===================================================
echo Nhấn phím bất kỳ để quay lại menu trước...
pause > nul
goto VIEW_TREE_MENU

:GIT_PULL
cls
echo ===================================================
echo      ĐANG CẬP NHẬT MÃ NGUỒN (GIT PULL)...
echo ===================================================
echo.
rem Kiểm tra xem Git có được cài đặt không
where git >nul 2>&1
if %errorlevel% neq 0 (
  echo ^>_<^ Lỗi: Git không được tìm thấy. Vui lòng cài đặt Git và đảm bảo nó nằm trong PATH của bạn.
) else (
  echo ^>^> Đang thực hiện "git pull" trong thư mục hiện tại.
  git pull
  if %errorlevel% equ 0 (
    echo.
    echo ^>^> Cập nhật mã nguồn thành công.
  ) else (
    echo.
    echo ^>_<^ Lỗi: Đã xảy ra lỗi khi cập nhật mã nguồn. Vui lòng kiểm tra lại.
  )
)
echo.
echo ===================================================
echo Nhấn phím bất kỳ để quay lại menu chính...
pause > nul
goto MAIN_MENU

:START_PROJECT
cls
echo ===================================================
echo     ĐANG KHỞI CHẠY DỰ ÁN (BACKEND & FRONTEND)
echo ===================================================
echo.

rem Chạy Spring Boot Backend bằng Maven Wrapper
echo ^>^> Đang khởi động Spring Boot Backend...
if exist "pos-backend\mvnw.cmd" (
  start "" cmd.exe /k "cd /d "pos-backend" && mvnw.cmd spring-boot:run"
  echo    Backend đã được khởi chạy trong cửa sổ mới.
) else (
  echo ^>_<^ Lỗi: Không tìm thấy "mvnw.cmd" trong thư mục "pos-backend".
  echo    Vui lòng kiểm tra lại cấu hình Maven Wrapper.
)
echo.

rem Chạy Vue.js Frontend (npm run dev)
echo ^>^> Đang khởi động Vue.js Frontend...
if exist "pos-frontend\package.json" (
  rem I've added an empty title argument to start to prevent issues with paths or commands with spaces
  rem start "" cmd.exe /k "cd /d "pos-frontend" && npm install && npm run dev"
  start "" cmd.exe /k "cd /d "pos-frontend" && npm run dev"
  echo    Frontend đã được khởi chạy trong cửa sổ mới.
) else (
  echo ^>_<^ Lỗi: Không tìm thấy "package.json" trong thư mục "pos-frontend".
  echo    Vui lòng kiểm tra lại dự án Vue.js.
)
echo.
echo ===================================================
echo Nhấn phím bất kỳ để quay lại menu chính...
pause > nul
goto MAIN_MENU

:END
cls
echo ===================================================
echo   CẢM ƠN BẠN ĐÃ SỬ DỤNG CHƯƠNG TRÌNH! HẸN GẶP LẠI!
echo ===================================================
timeout /t 1 > nul
endlocal
exit /b