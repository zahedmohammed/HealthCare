
export class Coverage{
    totalEndpoints: number;
    totalSuites: number;
    totalTestCases: number;

    endpoints = new Array<string>();
    countByMethod = new Array<TestSuiteCount>();
    countByCategory  = new Array<TestSuiteCount>();
    countBySeverity  = new Array<TestSuiteCount>();

}

export class TestSuiteCount{
    groupBy: string;
    count: number;
}