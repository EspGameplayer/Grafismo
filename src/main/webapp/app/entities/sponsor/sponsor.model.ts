export interface ISponsor {
  id?: number;
  name?: string;
  graphicsName?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  monocLogoContentType?: string | null;
  monocLogo?: string | null;
  details?: string | null;
  miscData?: string | null;
}

export class Sponsor implements ISponsor {
  constructor(
    public id?: number,
    public name?: string,
    public graphicsName?: string | null,
    public logoContentType?: string | null,
    public logo?: string | null,
    public monocLogoContentType?: string | null,
    public monocLogo?: string | null,
    public details?: string | null,
    public miscData?: string | null
  ) {}
}

export function getSponsorIdentifier(sponsor: ISponsor): number | undefined {
  return sponsor.id;
}
