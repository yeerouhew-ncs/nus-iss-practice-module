import moment from "moment";

export const mapGetEventList = (data: any, page: number) => {
  return {
    eventId: null,
    eventName: data.eventName,
    artistName: data.artistName,
    eventFromDt:
      data.eventFromDt !== null
        ? moment(data.eventFromDt).format("YYYY-MM-DD HH:mm:ss")
        : null,
    eventToDt:
      data.eventToDt !== null
        ? moment(data.eventToDt).format("YYYY-MM-DD HH:mm:ss")
        : null,
    planId: null,
    usesrId: null,
    page: page,
  };
};
