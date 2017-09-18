from locust import HttpLocust, TaskSet, task
import sys


class UserWorkflow(TaskSet):
    
    @task(1)
    def user_workflow(self):
        self.get_version()
        #sys.exit(0)
        
    def get_version(self):
        r = self.client.get('/v1/version')
        print(r.text)
        
        
        

class RunLocust(HttpLocust):
    task_set = UserWorkflow
    host = "http://localhost:1234"
    min_wait = 0
    max_wait = 0
    

    