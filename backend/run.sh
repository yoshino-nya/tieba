sudo systemctl start docker
docker start mysql
cd ~/Projects/java/tiebaFrontEnd/
npm run dev
cd ~/Projects/java/tieba/
docker-compose up -d