import dayjs from 'dayjs';
import moment from 'moment';

export const convertDateTimeFromServerToDateString = (date: Date): string => dayjs(date).format('DD/MM/YYYY');

// generate today's date as string DD/MM/YYYY
export const generateTodayDateAsDateString = (): string => {
  const dtToday: Date = new Date();
  const dTodayString: string = convertDateTimeFromServerToDateString(dtToday);
  return dTodayString;
};

export const twoWeekslater = (): any => {
  const today = moment();
  const twoWeeksLater = today.add(2, 'weeks');
  return twoWeeksLater;
};

export const isTwoWeeksLater = (date:any): any => {
  const today = moment();
  const twoWeeksLater = today.add(2, 'weeks').subtract(1,'days');
  return moment(date) >= twoWeeksLater;
};