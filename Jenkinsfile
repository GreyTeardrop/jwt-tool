pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Build') {
            steps {
                cache(maxCacheSize: 1024, caches: [
                        dependency(
                                path: './.m2/repository',
                                dependencyDescriptor: 'pom.xml',
                                includes: '**/*',
                                excludes: '')]) {
                    sh './mvnw -Dmaven.repo.local=./.m2/repository -B clean verify'
                }
            }
        }
    }
}
