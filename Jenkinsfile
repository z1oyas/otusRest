import groovy.json.JsonSlurperClassic

def testsStatistics = [:]

timeout(1200){
    node("maven") {
        try {
            def base_url = params.base_url
            def chatId = params.chat_id
            def botToken = params.bot_token

            stage("Prepare Allure results") {
                sh "mkdir -p allure-results"
            }
            stage("Running rest Automation") {
                def status = sh(
                        script: "docker run --name=rest_tests -e BASE_URL=$base_url --network=host -v /home/jenkins/workspace/rest-test-runner/allure-results:/app/allure-results localhost:5005/rest_tests:latest",
                        returnStatus: true
                )

                if (status > 0) {
                    currentBuild.result = 'UNSTABLE'
                }
            }

            stage("Debug allure mount") {
                sh "ls -la /home/jenkins/workspace/rest-test-runner/allure-results || true"
                sh "ls -la allure-results || true"
            }

            stage("Allure report publisher") {
                allure([
                        includeProperties: false,
                        jdk              : '',
                        properties       : [],
                        reportBuildPolicy: 'ALWAYS',
                        results          : [[path: 'target/allure-results']]
                ])
            }

//             stage("Gets statistics from allure artifacts") {
//                 def jsonLines = readFile "allure-report/widgets/summary.json"
//                 def slurped = new JsonSlurperClassic().parseText(jsonLines)
//
//                 slurped.each{k, v ->
//                     testsStatistics[k] =v
//                 }
//
//             }


//             stage("Telegram notification") {
//                 def message = """=============REST TESTS RESULT ================
//                 base_url: $base_url
//                 """
//
//                 testsStatistics.each{k,v ->
//                     message += "\t\t$k: $v\n"
//                 }
//                 withCredentials([string(credentialsId: 'chat_id', variable: 'chatId'), string(credentialsId: 'bot_token',variable: 'botToken')]){
//                     sh """
//                     curl -X POST \
//                     -H 'Content-Type: application/json' \
//                     -d '{"chat_id": "$chatId", "text": "$message"}' \
//                     "https://api.telegram.org/bot$botToken/sendMessage"
//                     """
//                 }
            //}
        }
        finally {
            stage("Cleanup") {
                sh "docker rm -f rest_tests"
            }
        }
    }
}