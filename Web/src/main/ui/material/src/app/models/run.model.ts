export class Run {
  task: Task = new Task();
  runId: string;
  job: Jobs = new Jobs();
  regions: string;
  modifiedDate: string;
  stats : Map<string, number> = new Map<string, number>();
}

export class Task {
  status: string;
  startTime: string;
  totalTime: string;
  totalTests: number;
  totalTestCompleted: number;
  failedTests: number;
  description: string;
}

export class Jobs {
name: string;
environment: string;
tags: string;
regions: string;
issueTracker: Dto = new Dto();
nextFire: string;
}

export class Dto {
name: string;
}