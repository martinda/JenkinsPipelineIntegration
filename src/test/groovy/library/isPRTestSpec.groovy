package tests.library

import testSupport.PipelineTestBase
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import hudson.model.Result;


class isPRTestSpec extends PipelineTestBase {

    def "http request"() {

        given:
            WorkflowJob p = j.createProject(WorkflowJob.class, "p");

        when:
            p.setDefinition(new CpsFlowDefinition("""
                node() {
                    def response = httpRequest(
                        url: 'http://google.com/',
                        responseHandle: 'STRING',
                        validResponseCodes: '101:399'
                    )
                    println(response.content)
                    println(response.status)
                    response.close()
                }
                """
            ));

        then:
            j.assertBuildStatus(Result.SUCCESS,p.scheduleBuild2(0));
    }

}
