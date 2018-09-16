pipeline {
    agent {
        kubernetes {
            //cloud 'kubernetes'
            label 'jenkins-playground-slave'
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: jdk
    image: adoptopenjdk/openjdk8:jdk8u172-b11-alpine
    command: ['cat']
    tty: true
"""
        }
    }
    options {
        timestamps()
    }

    stages {
        stage('Build') {
            steps {
                container('jdk') {
//                    cache(maxCacheSize: 1024, caches: [
//                            dependency(
//                                    path: './.m2/repository',
//                                    dependencyDescriptor: 'pom.xml',
//                                    includes: '**/*',
//                                    excludes: '')]) {
                        sh './mvnw -Dmaven.repo.local=./.m2/repository -B clean verify'
//                    }
                }
            }
        }
    }
}
