export class Run {
  task: Task = new Task();
  runId: string;
}

export class Task {
  status: string;
  startTime: string;
  totalTime: string;
  totalTests: string;
  totalTestCompleted: string;
  failedTests: string;
  description: string;
}