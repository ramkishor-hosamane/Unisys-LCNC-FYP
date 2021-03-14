import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  image:string=  "assets/images/bg.jpg"
  constructor() { }

  ngOnInit(): void {
  }

}
