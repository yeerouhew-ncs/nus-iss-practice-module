export interface IQueueResponse {
    message: string;
    statusCode: string;
    ticketnumber: string;
}

export interface IQueueResponse2 {
    message: string;
    queueStatus: string;
}
export interface IQueueRequest {
    eventId: string| undefined;
    subjectId: string | undefined;
}
export interface IQueueRequest2 {
    eventId: string| undefined;
    subjectId: string | undefined;
    ticketnumber: string | undefined;
}