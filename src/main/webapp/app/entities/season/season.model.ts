import dayjs from 'dayjs/esm';

export interface ISeason {
  id?: number;
  name?: string;
  graphicsName?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
}

export class Season implements ISeason {
  constructor(
    public id?: number,
    public name?: string,
    public graphicsName?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null
  ) {}
}

export function getSeasonIdentifier(season: ISeason): number | undefined {
  return season.id;
}
