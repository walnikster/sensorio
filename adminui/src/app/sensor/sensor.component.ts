import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Component, OnInit } from '@angular/core'
import { Observable } from 'rxjs'
import { FormGroup, FormBuilder, Validators } from '@angular/forms'
class SensorData {
  id: number
  temp: number
  time: any
}
@Component({
  selector: 'app-sensor',
  templateUrl: './sensor.component.html',
  styleUrls: ['./sensor.component.css']
})
export class SensorComponent implements OnInit {
  constructor(private httpClient: HttpClient, private fb: FormBuilder) {}

  sensordataObservable: Observable<SensorData[]>
  form: FormGroup
  isEditMode: boolean
  id: number
  jsonHeaders = new HttpHeaders({ 'Content-Type': 'application/json;' })
  baseUrl = 'http://localhost:3000/temperatures'

  ngOnInit() {
    this.form = this.fb.group({
      id: ['', Validators.required],
      temp: ['', Validators.required],
      time: ['', [Validators.required]]
    })
    this.isEditMode = false
    this.reload()
  }

  reload() {
    this.sensordataObservable = this.httpClient.get<SensorData[]>(`${this.baseUrl}`)
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
      this.httpClient.post<SensorData>(`${this.baseUrl}`, this.form.value, { headers: this.jsonHeaders }).subscribe(
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

  private resetToNew() {
    this.form.reset()
    this.id = null
    this.isEditMode = false
    this.form.get('id').enable()
  }

  private save() {
    if (!this.form.valid) {
      console.log('invalid form')
    } else {
      this.httpClient.put<SensorData>(`${this.baseUrl}/${this.id}`, this.form.value, { headers: this.jsonHeaders }).subscribe(
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

  forEdit(sensordata: SensorData) {
    this.isEditMode = true

    this.form.patchValue({
      id: sensordata.id,
      temp: sensordata.temp,
      time: sensordata.time
    })
    this.form.get('id').disable()
    this.id = sensordata.id
  }
}
