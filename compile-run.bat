@echo off
REM ============================================================
REM  Responsi Akhir PBO - Muhamad Fadilah Fikri (H1D025050)
REM  Compile dan jalankan sistem game "Petualangan Lembah Soedirman"
REM ============================================================
setlocal

cd /d "%~dp0"

echo [1/3] Membersihkan folder bin...
if exist bin rmdir /s /q bin
mkdir bin

echo [2/3] Mengompilasi seluruh source code...
javac -d bin src\*.java
if errorlevel 1 (
    echo.
    echo [GAGAL] Kompilasi gagal. Periksa pesan error di atas.
    pause
    exit /b 1
)

echo [3/3] Menjalankan program (mode pembuktian)...
echo.
java -cp bin Main

echo.
echo ============================================================
echo  Selesai. Untuk bermain game interaktif, jalankan:
echo      java -cp bin Main game
echo ============================================================
pause
endlocal
