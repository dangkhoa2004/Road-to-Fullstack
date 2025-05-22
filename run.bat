@echo off

REM Chạy Spring Boot Backend bằng Maven Wrapper
start cmd.exe /k "cd /d "pos-backend" && mvnw.cmd spring-boot:run"

REM Chạy Vue.js Frontend (npm run serve)
start cmd.exe /k "cd /d "pos-frontend" && npm run dev"

echo "Backend and Frontend are starting in separate windows..."
exit