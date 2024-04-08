import {Category} from "../../plan/containers/admin-container/PlanCreate";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {EventResponse} from "../../../interfaces/event-interface";
import {useAuthContext} from "../../../context/AuthContext";

import {IQueueRequest, IQueueRequest2, IQueueResponse, IQueueResponse2} from "../../../interfaces/queue-interface";
import {CHECK_QUEUE_URL, joinQueue} from "../queue.api";
import axios from "axios";

type SeatingPlanType = {
    row: number;
    col: number;
    planName: string;
    venueName: string;
    sectionSeats: Category[];
};
const QueueView:  React.FC = () => {
    const param = useParams();
    const eventId = param?.eventId;


    const [errors, setErrors] = useState<boolean>(false);
    const [errorMsg, setErrorMsg] = useState<string>("");
    const [warning, setWarning] = useState<boolean>(false);
    const [event, setEvent] = useState<IQueueResponse>();

    const navigate = useNavigate();
    const { userInfo } = useAuthContext();
    // var ws = new WebSocket();
    const mappedRequest: IQueueRequest = {
        eventId: eventId,
        subjectId: userInfo?.id,
    };
    const joinEventQueue = async () => {
        console.log("userinfo")

        console.log("joinEventQueue: ",mappedRequest);

        try {
            const response: IQueueResponse = await joinQueue(mappedRequest);
            console.log("response: ",response);

            if (response.message === "SUCCESS" ) {
                console.log("message: ",response);
                console.log("ticketNumber: ",response.ticketnumber);
                setEvent(response);
                const mappedRequest2 :IQueueRequest2 = {
                    eventId: mappedRequest?.eventId,
                    subjectId: mappedRequest?.subjectId,
                    ticketnumber: response.ticketnumber
                };
                console.log("mappedRequest2",mappedRequest2);
                setTimeout(() => fetchDataAndCheckStatus(mappedRequest2), 10000);
            }
        } catch (error) {
            // TODO: error handling
            console.log("error",error);
        }
    };
    const fetchDataAndCheckStatus = async (queueRequest: IQueueRequest2): Promise<IQueueResponse2> => {


        const { data } = await axios.post<IQueueResponse2>(CHECK_QUEUE_URL,queueRequest);
        let expectedStatus = "PROCESSING";

        console.log("retrieve queue status",data)
        const currentStatus = data.queueStatus;

        // Check if there's a change in status
        if (expectedStatus == currentStatus) {
            console.log('Status changed:', currentStatus);
            // TODO : change navigate to purchase page
            navigate("/")

        }
        return data;
    };



    useEffect(() => {
        // client.connect("admin", "guest", on_connect, on_error, "/");
        console.log("subjectId: userInfo?.id,",userInfo);
        joinEventQueue().then(r => console.log(r));

        // fetchDataAndCheckStatus(mappedRequest2).then(r => console.log("fetched"));
        // setInterval(fetchDataAndCheckStatus(mappedRequest2), 30000);
        //
    }, []);


    return (
        <div>
            In Queue

        </div>
    )

};

export default QueueView;