import { Component, OnInit, Input } from '@angular/core';
import { Picture } from '../../dtos/picture';
import {PictureService} from '../../services/picture.service';

@Component({
  selector: 'app-picture-display',
  templateUrl: './picture-display.component.html',
  styleUrls: ['./picture-display.component.scss']
})
export class PictureDisplayComponent implements OnInit {
  @Input() ids: number[];
  pictures: any[];
  constructor(private pictureService: PictureService) {
    this.pictures = new Array();
  }
  ngOnInit() {
    for (const id of this.ids) {
      this.getPictureFromService(id);
    }
  }
  createPictureFromBlob(picture: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.pictures.push(reader.result);
    }, false);

    if (picture) {
      reader.readAsDataURL(picture);
    }
  }

  getPictureFromService(id: number) {
    console.log('Load picture with id ' + id);
    this.pictureService.getPicture(id).subscribe(data => {
      this.createPictureFromBlob(data);
    }, error => {
      console.log(error);
    });
  }

}
