import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {Client} from '../../dtos/client';
import {ClientService} from '../../services/client.service';

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.scss']
})
export class ClientAddComponent implements OnInit {

  public queryParams: Client;

  public searchForm = this.formBuilder.group({
    name: [''],
    surname: [''],
    email: ['']
  });

  constructor(private router: Router, private clientService: ClientService, private formBuilder: FormBuilder) {

  }

  ngOnInit() {
    this.queryParams = new Client(null, '', '', '');
  }


  /**
   * Sends client creation request
   */
  createClient() {
    this.clientService.createClient(this.queryParams).subscribe(
      () => {
        this.router.navigate(['/client-filter']);
      }
    );
  }

}
