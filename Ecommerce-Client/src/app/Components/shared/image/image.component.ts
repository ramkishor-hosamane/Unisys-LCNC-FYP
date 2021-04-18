import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'image',
  templateUrl: './image.component.html',
  styles: [
  ]
})
export class ImageComponent implements OnInit {

  constructor() { }
  @Input("url") url:String="";
  @Input("width") width:String="";
  @Input("height") height:String="";

  ngOnInit(): void {
  }

}
