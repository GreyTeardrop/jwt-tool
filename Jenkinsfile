pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Build') {
            steps {
                withCache(keyFilePath: './pom.xml', cacheDirPath: './.m2/repository') {
                    sh './mvnw -Dmaven.repo.local=./.m2/repository -B verify'
                }
            }
        }
    }
}
