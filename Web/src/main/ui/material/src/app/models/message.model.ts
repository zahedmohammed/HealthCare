export class Message {

    constructor(
    public subject: string,
    public type: string,
    public createdDate: string,
    public taskType: string,
    public status: string,
    public message: string
    ) {}
}