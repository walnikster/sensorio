import { HttpClient } from '@angular/common/http'
import { Component, OnInit } from '@angular/core'
import { Observable } from 'rxjs'
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
  baseUrl = 'http://localhost:3000/temperatures'

  ngOnInit() {
    this.reload()
  }

  reload() {
    this.sensordataObservable = this.httpClient.get<SensorData[]>(`${this.baseUrl}`)
  }
}
