import {Category} from "../../plan/containers/admin-container/PlanCreate";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {EventResponse} from "../../../interfaces/event-interface";
import {useAuthContext} from "../../../context/AuthContext";

type SeatingPlanType = {
    row: number;
    col: number;
    planName: string;
    venueName: string;
    sectionSeats: Category[];
};
const QueueView: () => void = () => {
    const param = useParams();
    const eventId = param?.eventId;


    const [errors, setErrors] = useState<boolean>(false);
    const [errorMsg, setErrorMsg] = useState<string>("");
    const [warning, setWarning] = useState<boolean>(false);
    const [event, setEvent] = useState<EventResponse>();

    const navigate = useNavigate();
    const { userInfo } = useAuthContext();



};

export default QueueView;