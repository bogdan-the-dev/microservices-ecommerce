import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AuthRedirectService {
  private redirectUrl: string;

  setRedirectUrl(url: string): void {
    this.redirectUrl = url;
  }

  getRedirectUrl(): string {
    const redirectUrl = this.redirectUrl;
    this.redirectUrl = null; // Clear the stored redirect URL
    return redirectUrl;
  }
}
