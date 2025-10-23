import axios from 'axios'
import { openSuccess } from '../alerts'
import { createAction, handleActions } from 'redux-actions'


const actions = {
  INVENTORY_GET_ALL: 'inventory/get_all',
  INVENTORY_GET_ALL_PENDING: 'inventory/get_all_PENDING',
  INVENTORY_SAVE: 'inventory/save',
  INVENTORY_DELETE: 'inventory/delete',
  INVENTORY_REFRESH: 'inventory/refresh',
  INVENTORY_GET_UM: 'inventory/get_um',
  INVENTORY_GET_UM_FULFILLED: 'inventory/get_um_FULFILLED',
}

export let defaultState = {
  all: [],
  um: [],
  umFetched: false,
}

export const findInventory = createAction(actions.INVENTORY_GET_ALL, () =>
  (dispatch, getState, config) => axios
    .get(`${config.restAPIUrl}/inventory`)
    .then((suc) => {
      dispatch(refreshInventory(suc.data))
      dispatch(openSuccess('Success'))
    })
)

export const saveInventory = createAction(actions.INVENTORY_SAVE, (inventory) =>
  (dispatch, getState, config) => axios
    .post(`${config.restAPIUrl}/inventory`,
      {
        id: null,
        //version: 1,
        name: 'testcreate1',
        productType: 'test',
        description: 'word',
        averagePrice: 12.00,
        amount: 0.80,
        unitOfMeasurement: 2,
        bestBeforeDate: null,
        neverExpires: true,
        availableStores: ['Walmart']
      }
    )
    .then((suc) => {
      const invs = []
      getState().inventory.all.forEach(inv => {
        if (inv.id !== suc.data.id) {
          invs.push(inv)
        }
      })
      invs.push(suc.data)
      dispatch(findInventory())
      dispatch(openSuccess('Saved success'))
    })
)

export const refreshInventory = createAction(actions.INVENTORY_REFRESH, (payload) =>
  (dispatcher, getState, config) =>
    payload.sort((InvA, InvB) => InvA.name < InvB.name ? -1 : InvA.name > InvB.name ? 1 : 0)
)

export const findUnitOfMeasurements = createAction(actions.INVENTORY_GET_UM, () =>
  (dispatch, getState, config) => axios.get(`${config.restAPIUrl}/inventory/unitOfMeasurements`)
    .then((suc) => {
      dispatch({ type: actions.INVENTORY_GET_UM_FULFILLED, payload: suc.data })
    }
    )
)

export default handleActions({
  [actions.INVENTORY_GET_ALL_PENDING]: (state) => ({
    ...state,
    fetched: false
  }),
  [actions.INVENTORY_REFRESH]: (state, action) => ({
    ...state,
    all: action.payload,
    fetched: true,
  }),
  [actions.INVENTORY_GET_UM_FULFILLED]: (state, action) => ({
    ...state,
    um: action.payload,
    umFetched: true
  }),
}, defaultState)
