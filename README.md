# charging.station.management

# Steps to run the Project

Pre-requisites
1. Java 14
2. Docker & Docker-compose
3. Tool to open the project like Intellij, Eclipse , etc

Run application
1. Run the Docker compose file available at the root directory as per below detailed instructions
2. Open the project in any tool like IntelliJ and configure java 14
3. Run the application, it will connect to the PostgreSQL database deployed on docker container in the step 1

# Tech Stack

1. Spring Boot with Java 14 (Rest APIs)
2. Actuator (Health Check)
3. Liquibase (DB Migration Tool)
4. Open API - Swagger (API Documentation)
5. Github (Code Management)
6. Docker
7. Docker based deployment
8. Kubernetes
9. PostgreSQL database
10. PgAdmin database UI

# Information of deployment on Docker containers
Docker compose file is available in the project root directory, which will deploy the following containers

	1. PostgreSQL Database 
	2. PgAdmin - Database UI
	
	Command to run the docker compose file is below (run this command from the root directory)
	-> docker-compose up
	
	To connect PgAdmin to the PostgreSQL database, use the following details (login details are from the docker-compose file)
	  URL: http://localhost:9090/
	  User: abubakar.cs@gmail.com
	  Pass: root
	
	And for connectivity with the database, use the following details from the docker-compose file
		POSTGRES_USER: postgres
		POSTGRES_PASSWORD: postgres
		POSTGRES_DB: station_management_db
	  	Host Name: postgres  (this is the servise name defined in the compose file that we use for connectivity of containers in the same network)


Docker file for this project is available at the root directory

Commands are below to build and run the docker image (from the directory where docker file is placed)

	1) docker build --tag=charging-station-management:latest . 
	2) docker run -d --name station_management_container --network=station_management_db_network -e DB_HOST=postgres -p8081:8080 charging-station-management:latest 

	Note: 
		1) External port (8081) can be changed according to the requirement, it could be any
		2) Here, using network to connect with PostgreSQL container that is linked with the same
		3) This part (--network=station_management_db_network -e DB_HOST=postgres) from the above command II will be removed in the case when database will be on the host machine or connecting remotely. 
		This mentioned part is only usefull when we need to connect our application container to database that is also deployed on another container.


# Web-UI
I have implemented a simple user interface using html and javascript to call the back-end APIs programmatically.
Just need to double click the index page (from the directoy **../src/main/resources/Web-UI**) and it will show all the links which are basically mapped to each backend APIs and performs the relevant operation.	


# Kubernetes

I have created kubernetes configuration files in the directory (**../src/main/resources/kubernetes/**). I did setup locally using **Minikube** and all steps for installaion and configuration are mentioned in the below section in details.

Need to run in the following commands in the same order (from the directory **../src/main/resources/kubernetes/**) to deploy application along with PostgreSQL on the kubernetes pods.

All pods related configurations are in the yml files which will be used for deployment.

	1. kubectl apply -f postgres-configmap.yml  	[postgres database info]
	
		check status: kubectl get ConfigMaps
		
	2. kubectl apply -f postgres-credentials.yml	[postgres credentials]
	
		check status: kubectl get secrets
	
	3. kubectl apply -f postgres-deployment.yml		[postgres pod deployment]
	
		check status: kubectl get deployments, kubectl get pods
		
		To access the data base
		
			kubectl exec -it pod-name bash
			psql -h postgres -U postgres
			\l  (to view databases)
			\q  (to exit the databases)
	
	1. kubectl apply -f app-deployment.yml

		check status: kubectl get deployments, kubectl get pods, kubectl logs pod-name
		

# Minikube(Kubernetes) Setup locally and other commands for information

1. Download/install the package depending on your platform (Windows, Mac, Linux, etc), I am using Windows here

2. Add the binary in to your PATH (Make sure to run PowerShell as Administrator)

	$oldPath = [Environment]::GetEnvironmentVariable('Path', [EnvironmentVariableTarget]::Machine)
	if ($oldPath.Split(';') -inotcontains 'C:\minikube'){ `
	  [Environment]::SetEnvironmentVariable('Path', $('{0};C:\minikube' -f $oldPath), [EnvironmentVariableTarget]::Machine) `
	}
	
	To check minikube version
	
	-> minikube version

3. To run/stop the minikube

	-> minikube start  (it will automatically pick the docker as a driver, docker should have been installed)
	-> minikube stop
	
	To remove/uninstall the minikube
		-> minikube delete
	
	Alternatively, we can run with driver name as well like
	
		-> minikube start --driver=docker

4. To check the minikube status

	-> minikube status

5. To check the cluster info (kubectl automatically installed with minikube)

	-> kubectl cluster-info
	
6. To get the node info

	-> kubectl get node	

6. To allow the minikube to read the docker repository, need to run the following command

	-> minikube docker-env
		(and then run the last command from the result of above command)
	
7. To deploy the project on the kubernetes cluster, we need deployment object and it can be created in two ways

	i) Deploy using direct command
	
		-> kubectl create deployment deployment-name --image=docker-image-name:version --port:port-number
		
		To load image into minikube (manually)
		
			-> minikube image load image-name:version
		
		To check the deployment
		
			-> kubectl get deployment
			-> kubectl delete deployment deployment-name
		
		To see the description of the deployment

			-> kubectl describe deployment deployment-name
	
	ii) Using the yml file	[this is standard way]
	
		we will create a yml file and then run the following command 
		
		-> kubectl apply -f name-of-file.yml  (in the directory where file exists)

8. To get the pods info

	-> kubectl get pods
	-> kubectl logs pod-name
	-> kubectl delete pod pod-name
	
9. To access urls in the pods we need to create a service object that will expose our current deployment	

	-> kubectl expose deployment deployment-name --type=NodePort

10. To get the service info

	-> kubectl get service/svc

11. To access the urls external, we need service url in this way

	To check the internal IP of the service
		-> get nodes -o wide
		-> minikube service service-name --url
		
	To get the external IP (using type = LoadBalancer)	
	    -> minikube tunnel      (in a separate terminal)

12. To setup the kubernetes dashboard

	-> minikube dashboard  (it will generate the dashboard url)	
	
# Heroku Deployment

I have also added configurations for auto deployment on **Heroku cloud** using the **Github actions** pipeline. Every time project will be auto deploy on every commit in the main branch

Swagger URL on Heroku Cloud: https://station-management.herokuapp.com/swagger-ui/index.html#/

**NOTE:** Application is deployed on Heroku cloud, APIs are accesible.
