export class Match {
    club1: string;
    club2: string;
    club1Goals: number;
    club2Goals: number;
    date: Date;

    getDate(): Date {
        return this.date;
    }
}
