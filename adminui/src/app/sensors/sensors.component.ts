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
  jsonHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  baseUrl = 'http://localhost:8080/sensorio/resources/sensors'

  ngOnInit() {
    this.form = this.fb.group({
      sensorId: ['', Validators.required],
      name: ['', [Validators.required]]
    })
    this.isEditMode = false
    this.reload()
  }

  reload() {
    this.sensorsObservable = this.httpClient.get<Sensor[]>(`${this.baseUrl}`, { headers: this.jsonHeaders })
    this.resetToNew()
  }

  saveorupdate() {
    if (this.isEditMode) {
      this.save()
    } else {
      this.create()
    }
  }

  private create() {
    if (!this.form.valid) {
      console.log('invalid form')
    } else {
      this.httpClient.post<Sensor>(`${this.baseUrl}`, this.form.value, { headers: this.jsonHeaders }).subscribe(
        data => {
          console.log('POST response received', data)
        },
        err => {
          console.error('POST error received', err)
        },
        () => {
          this.reload()
          this.resetToNew()
        }
      )
    }
  }

  private save() {
    if (!this.form.valid) {
      console.log('invalid form')
    } else {
      this.httpClient.put<Sensor>(`${this.baseUrl}/${this.id}`, this.form.value, { headers: this.jsonHeaders }).subscribe(
        data => {
          console.log('PUT response received', data)
        },
        err => {
          console.error('PUT error received', err)
        },
        () => {
          this.reload()
          this.resetToNew()
        }
      )
    }
  }

  private resetToNew() {
    this.form.reset()
    this.id = null
    this.isEditMode = false
    this.form.get('id').enable()
  }

  delete(id) {
    this.httpClient.delete(`${this.baseUrl}/${id}`, { headers: this.jsonHeaders }).subscribe(
      data => {
        console.log('DELETE response received', data)
      },
      err => {
        console.error('DELETE error received', err)
      },
      () => {
        this.reload()
        this.resetToNew()
      }
    )
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
