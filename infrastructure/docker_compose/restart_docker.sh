#!/bin/bash
echo ">>> stop docker compose"
docker compose down

echo ">>> docker pull all images browsers"
docker pull selenoid/vnc:firefox_89.0
docker pull selenoid/vnc:chrome_91.0
docker pull selenoid/vnc:opera_76.0

echo ">>> run docker compose"
docker compose up -d
