import { HttpClient } from '@angular/common/http'
import { Component, OnInit } from '@angular/core'
import { Observable } from 'rxjs'
import { tap } from 'rxjs/operators'
class SensorData {
  id: number
  temperature: number
  time: any
}
@Component({
  selector: 'app-sensor',
  templateUrl: './sensor.component.html',
  styleUrls: ['./sensor.component.css']
})
export class SensorComponent implements OnInit {
  constructor(private httpClient: HttpClient) {}

  sensordataObservable: Observable<SensorData[]>

  ngOnInit() {
    this.sensordataObservable = this.httpClient.get<SensorData[]>('http://localhost:3000/temperatures')
  }

  reload() {
    this.sensordataObservable = this.httpClient.get<SensorData[]>('http://localhost:3000/temperatures')
  }
}
