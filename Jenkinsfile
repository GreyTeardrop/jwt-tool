pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Build') {
            steps {
                sh './mvnw -B verify'
            }
        }
    }
}
