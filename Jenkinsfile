pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Build') {
            steps {
                sh './mvnw -Dmaven.repo.local=./.m2/repository -B verify'
            }
        }
    }
}
