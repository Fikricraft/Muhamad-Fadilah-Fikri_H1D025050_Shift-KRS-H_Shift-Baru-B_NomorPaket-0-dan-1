#!/usr/bin/env bash
# ============================================================
#  Responsi Akhir PBO - Muhamad Fadilah Fikri (H1D025050)
#  Compile dan jalankan sistem game "Petualangan Lembah Soedirman"
# ============================================================
set -e

cd "$(dirname "$0")"

echo "[1/3] Membersihkan folder bin..."
rm -rf bin
mkdir -p bin

echo "[2/3] Mengompilasi seluruh source code..."
javac -d bin src/*.java

echo "[3/3] Menjalankan program (mode pembuktian)..."
echo
java -cp bin Main

echo
echo "============================================================"
echo " Selesai. Untuk bermain game interaktif, jalankan:"
echo "     java -cp bin Main game"
echo "============================================================"
