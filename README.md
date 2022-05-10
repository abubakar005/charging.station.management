# charging.station.management

Docker compose file is avilable in the project main directory, which will deploy the following containers
	1. PostgreSQL Database
	2. PgAdmin - Database UI
	
	Command to run the docker compose file is below (run this command from the main directory)
	docker-compose up
	
	To connect PgAdmin to the PostgreSQL database, need to run the following command which will give you the IP address of the PostgreSQL docker container (Note: As both these are deployed on Docker so they can not be accessible using the localhost)
	docker inspect container-Id (PostgreSQL)
	
	At the end of the command result, IP address will be as below (under gateway IP)
	
	"IPAddress": "172.18.0.2",
	
Docker file for this project is avilable at the main directory

Docker commands are below to build and run the docker image (from the directory where docker is file placed)
	i) docker build --tag=charging-station-management:latest . 
	ii) docker run -d --name station_management_container --network=station_management_db_network -e DATABASE.URL=postgres -p8081:8080 charging-station-management:latest 

	
	Note: 
		External port (8081) can be changed according to the requirement
		Here, using network to connect with PostgreSQL container that is linked with the same