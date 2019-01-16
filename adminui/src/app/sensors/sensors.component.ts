import { tap } from 'rxjs/operators'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Component, OnInit } from '@angular/core'
import { Observable } from 'rxjs'
import { FormGroup, FormBuilder, Validators } from '@angular/forms'

class Sensor {
  id?: number
  sensorId: string
  name: string
}
@Component({
  selector: 'app-sensors',
  templateUrl: './sensors.component.html',
  styleUrls: ['./sensors.component.css']
})
export class SensorsComponent implements OnInit {
  constructor(private httpClient: HttpClient, private fb: FormBuilder) {}

  sensorsObservable: Observable<Sensor[]>
  form: FormGroup
  isEditMode: boolean
  id: number

  ngOnInit() {
    this.sensorsObservable = this.httpClient.get<Sensor[]>('http://localhost:8080/sensorio/resources/sensors')
    this.form = this.fb.group({
      sensorId: ['', Validators.required],
      name: ['', [Validators.required]]
    })
    this.isEditMode = false
  }

  reload() {
    this.sensorsObservable = this.httpClient.get<Sensor[]>('http://localhost:8080/sensorio/resources/sensors')
  }

  saveorupdate() {
    if (this.isEditMode) {
      this.save()
    } else {
      this.create()
    }
  }

  create() {
    if (!this.form.valid) {
      console.log('invalid form')
    } else {
      this.httpClient.post<Sensor>('http://localhost:8080/sensorio/resources/sensors', this.form.value).subscribe(data => {
        this.reload()
        this.form.reset()
        this.id = null
        this.isEditMode = false
      })
    }
  }

  save() {
    if (!this.form.valid) {
      console.log('invalid form')
    } else {
      this.httpClient.put<Sensor>(`http://localhost:8080/sensorio/resources/sensors/${this.id}`, this.form.value).subscribe(data => {
        this.reload()
        this.form.reset()
        this.id = null
        this.isEditMode = false
      })
    }
  }

  delete(id) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json;' })
    this.httpClient.delete(`http://localhost:8080/sensorio/resources/sensors/${id}`, { headers: headers }).subscribe(data => {
      this.reload()
    })
  }

  forEdit(sensor: Sensor) {
    this.isEditMode = true
    this.form = this.fb.group({
      sensorId: sensor.sensorId,
      name: sensor.name
    })
    this.id = sensor.id
  }
}
