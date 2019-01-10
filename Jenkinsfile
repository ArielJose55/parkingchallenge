pipeline {
 
	agent {
		label 'Slave_Induccion' 
	}

	options {
		buildDiscarder(logRotator(numToKeepStr: '3'))

		disableConcurrentBuilds()
	}
	
	tools {
		jdk 'JDK8_Centos' 
		gradle 'Gradle4.5_Centos' 
	}
	
	//Aquí­ comienzan los stages del Pipeline
	stages{
		stage('Checkout') {
			steps{
				echo "------------>> Checkout <<------------"
				checkout([$class: 'GitSCM ', branches: [[name: '*/master']] , 
				doGenerateSubmoduleConfigurations: false, extensions: [], gitTool: 'Git_Centos' ,
				submoduleCfg: [], userRemoteConfigs: [[ credentialsId: 'GitHub_ArielJose55',
		 	 	url: 'https://github.com/ArielJose55/parkingchallenge']]])
		 	 	sh 'gradle clean'
			}
		}
		
		stage('Compile') {
			steps{
				echo "------------>> Compile has started <<------------"
				sh 'gradle --b ./build.gradle compileJava'
			}
		}
	
		stage('Unit Tests') {
			steps{
				echo "------------>> Unit Tests has started <<------------"
				sh 'gradle test'
				junit '**/build/test-results/test/*.xml' 
				step( [ $class: 'JacocoPublisher' ] )
			}
		}
				
		/*stage('Integration Tests') {
			steps {
				echo "------------>> Integration Tests  has started <<------------"
			}
		}*/
	
		stage('Static Code Analysis') {
			steps{
				echo "------------->> Static Code Analysis has started <<--------------"
				withSonarQubeEnv('Sonar') {
					sh "${tool name: 'SonarScanner', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
				}
			}
		}
		
		stage('Build') {
			steps {
				echo "------------>> Build has started <<------------"
				sh 'gradle --b ./build.gradle build -x test'
			}
		}
	}
	
	post {
		always {
			echo 'This will always run'
		}
		success {
			echo 'This will run only if successful'
			junit '**/build/test-results/test/*.xml'
		}
		failure {
			echo 'This will run only if failed'
			mail (to: 'ariel.arnedo@ceiba.com.co',
			      subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
			      body: "Something is wrong with ${env.BUILD_URL}")
		}
		unstable {
			echo 'This will run only if the run was marked as unstable'
		}
		changed {
			echo 'This will run only if the state of the Pipeline has changed'
			echo 'For example, if the Pipeline was previously failing but is now successful'
		}
	}
}