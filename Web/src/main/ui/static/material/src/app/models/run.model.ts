export class Run {
    task: Task = new Task();
    runId: string;
}

export class Task {
status: string;
startTime: string;
totalTime: string;
description: string;
}