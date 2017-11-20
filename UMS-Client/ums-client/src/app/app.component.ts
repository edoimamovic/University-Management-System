import { Component } from '@angular/core';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  username = "";
    
  constructor(public auth: AuthService){
  }


  public signOut(){
    this.auth.signOut();
    this.username = "";
  }

    
}
