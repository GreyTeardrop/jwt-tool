pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Build') {
            steps {
                cache(maxCacheSize: 1024, caches: [
                        arbitraryFile(path: './.m2/repository', includes: '**/*', excludes: '')
                ]) {
                    sh './mvnw -Dmaven.repo.local=./.m2/repository -B verify'
                }
            }
        }
    }
}
