import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {Client} from '../../dtos/client';
import {ClientService} from '../../services/client.service';

@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.scss']
})
export class ClientEditComponent implements OnInit {

  public queryParams: Client;

  public searchForm = this.formBuilder.group({
    id: [''],
    name: [''],
    surname: [''],
    email: ['']
  });

  constructor(private router: Router, private clientService: ClientService,
              private formBuilder: FormBuilder, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.clientService.getClientById(this.route.snapshot.paramMap.get('client')).subscribe(
      (client: Client) => {
        this.queryParams = client;
      },
      error => {
      }
    );
  }


  /**
   * Sends client update request
   */
  updateClient() {
    console.log(this.queryParams);
    this.clientService.updateClient(this.queryParams).subscribe(
      () => {
        this.router.navigate(['/client-filter']);
      }
    );
  }

}
