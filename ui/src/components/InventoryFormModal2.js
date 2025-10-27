import * as Yup from 'yup'
import Button from '@material-ui/core/Button'
import Checkbox from '@material-ui/core/Checkbox'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import Grid from '@material-ui/core/Grid'
import React from 'react'
import { ErrorMessage, Field, Form, Formik } from 'formik'
import { FormControlLabel, MenuItem, Select, TextField } from '@material-ui/core'

class InventoryFormModal extends React.Component {
  render() {
    const {
      formName,
      handleDialog,
      handleInventory,
      title,
      initialValues,
      productList,
      unitOfMeasList,
      handleChange
    } = this.props
    return (
      <Dialog
        open={this.props.isDialogOpen}
        maxWidth='sm'
        fullWidth={true}
        onClose={() => { handleDialog(false) }}
      >
        <Formik
          initialValues={initialValues}
          validationSchema={Yup.object({
            Name: Yup.string().required('Required'),
            ProductType: Yup.string().required('Required'),
            //UnitOfMeasurement: Yup.string().required('Required'),
          })}
          onSubmit={values => {
            handleInventory(values)
            handleDialog(true)
          }}>
          {helpers =>
            <Form
              autoComplete='off'
              id={formName}
            >
              <DialogTitle id='alert-dialog-title'>
                {`${title} Inventory`}
              </DialogTitle>
              <DialogContent>
                <Grid container columns={12}>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='Name'
                      label='Name'
                      as={TextField}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      as={Select}
                      name='ProductType'
                      label='Product Type'
                    >
                      {productList.map(prod =>
                        <MenuItem key={prod.id} value={prod.name}>{prod.name}</MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='Description'
                      label='Decscription'
                      as={TextField}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='AveragePrice'
                      label='Average Price'
                      as={TextField}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='Amount'
                      label='Amount'
                      as={TextField}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, select: true }}
                      name='UnitOfMeasurement'
                      label='Unit of Measurement'
                      as={Select}
                    >
                      {unitOfMeasList.map(um =>
                        <MenuItem
                          className='Select' key={um.id} value={um.name}>{um.name}</MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='BestBeforeDate'
                      label='Best Before Date'
                      as={TextField}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='NeverExpires'
                      label='Never Expires'
                      //as={Checkbox}
                      as={Checkbox}
                      //as={() => <FormControlLabel control={<Checkbox />} label="Never Expires" />}
                      //as={() => <FormControlLabel control={<Checkbox name='NeverExpires' />} label="Never Expires" />}
                    />
                  </Grid>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button onClick={() => { handleDialog(false) }} color='secondary'>Cancel</Button>
                <Button
                  disableElevation
                  variant='contained'
                  type='submit'
                  form={formName}
                  color='secondary'
                  disabled={!(helpers.isValid && helpers.dirty) }
                >
                  Save
                </Button>
              </DialogActions>
            </Form>
          }
        </Formik>
      </Dialog>
    )
  }
}

export default InventoryFormModal