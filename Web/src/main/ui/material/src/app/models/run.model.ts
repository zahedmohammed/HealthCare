export class Run {
  task: Task = new Task();
  runId: string;
  modifiedDate: string;
  stats : Map<string, number> = new Map<string, number>();
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