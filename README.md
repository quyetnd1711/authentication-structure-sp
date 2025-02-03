# Setup
### PostgreSQL and Redis
```
docker compose -f docker-compose.yml -p authentication-structure-sp up -d
```
# Deployment
### Step 1
```
docker build -t authentication-structure-sp .
```
### Step 2
```
docker run -d -p 1711:1711 --name authentication-stu authentication-structure-sp
```

