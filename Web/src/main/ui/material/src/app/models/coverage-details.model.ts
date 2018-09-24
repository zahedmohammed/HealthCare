export class CoverageDetails{
    totalEndpoints: number;
    totalSuites: number;
    totalTestCases: number;

    endpoints = new Array<string>();
    countByMethod = new Array<TestSuiteCount>();
    countByCategory  = new Array<TestSuiteCount>();
    countBySeverity  = new Array<TestSuiteCount>();

    coverage = new Array<Coverage>();

}

export class TestSuiteCount{
    groupBy: string;
    count: number;
}

export class Coverage{

    category: string;

    coveredEndpoints: number;
    totalEndpoints: number;
    tsCount: number;
    relevantEndpoints: number;
    coveragePercentage: number;

}