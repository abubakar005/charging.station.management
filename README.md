# charging.station.management

Docker compose file is avilable in the project main directory, which will deploy the following containers
	1. PostgreSQL Database
	2. PgAdmin - Database UI
	
	Command to run the docker compose file is below (run this command from the main directory)
	docker-compose up
	
	To connect PgAdmin to the PostgreSQL database, use the following details (login details are from the docker-compose file)
	  URL: http://localhost:9090/
	  User: abubakar.cs@gmail.com
	  Pass: root
	
	And for connectivity with the database, use the following details from the docker-compose file
	  POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: station_management_db
	  Host Name: postgres  (this is the servise name defined in the compose file that we use for connectivity of containers in the same network)
	
Docker file for this project is avilable at the main directory

Commands are below to build and run the docker image (from the directory where docker is file placed)
	I) docker build --tag=charging-station-management:latest . 
	II) docker run -d --name station_management_container --network=station_management_db_network -e DATABASE.URL=postgres -p8081:8080 charging-station-management:latest 

	Note: 
		1) External port (8081) can be changed according to the requirement, it could be any
		2) Here, using network to connect with PostgreSQL container that is linked with the same
		3) This part (--network=station_management_db_network -e DATABASE.URL=postgres) from the above command II will be removed in the case when database will be on the host machine or connecting remotely. 
		This mentioned part is only usefull when we need to connect our application container to database that is also deployed on another container.

I have implemented a simple user interface using html and javascript to call the back-end APIs programtically.
Just need to double click the index page (Web-Ui in the resources folder) and it will show all the links which are basically mapped to each backend APIs and performs the relevant opration.	