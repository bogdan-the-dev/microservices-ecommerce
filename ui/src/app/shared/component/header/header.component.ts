import {Component} from "@angular/core";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.less']
})
export class HeaderComponent {

  searchValue: string = ''

  constructor(private router: Router) {

  }


  onSearch() {
    this.router.navigate(['search', this.searchValue])

  }
}
