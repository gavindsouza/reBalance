<template>
  <div class="max-w-3xl py-12 mx-auto grid gap-4 grid-cols-2">
    <Card title="Important" subtitle="" class="col-span-2">
      <ListItem title="List Item 1" subtitle="">
        <template #actions>
          <Dropdown
            :items="[{ label: 'Open on Device' }, { label: 'Dismiss' }]"
          >
            <template v-slot="{ toggleDropdown }">
              <Button @click="toggleDropdown()" icon="more-horizontal" />
            </template>
          </Dropdown>
        </template>
      </ListItem>
      <ListItem title="List Item 2" subtitle="Sub text 2" />
    </Card>

    <Card title="Updates" subtitle="Sub text" class="col-span-2">
      <ListItem title="List Item 1" subtitle="Sub text 1">
        <template #actions>
          <Button icon="more-horizontal" />
        </template>
      </ListItem>
      <ListItem title="List Item 2" subtitle="Sub text 2" />
    </Card>

    <Card title="Forums" subtitle="Sub text">
      <ListItem title="List Item 1" subtitle="Sub text 1">
        <template #actions>
          <Button icon="more-horizontal" />
        </template>
      </ListItem>
      <ListItem title="List Item 2" subtitle="Sub text 2" />
    </Card>

    <Card title="Promotions" subtitle="Sub text">
      <ListItem title="List Item 1" subtitle="Sub text 1">
        <template #actions>
          <Button icon="more-horizontal" />
        </template>
      </ListItem>
      <ListItem title="List Item 2" subtitle="Sub text 2" />
    </Card>
  </div>
</template>

<script>
import { Dialog, Card, ListItem, Button, Dropdown } from 'frappe-ui'

export default {
  name: 'Home',
  data() {
    return {
      showDialog: false,
    }
  },
  resources: {
    ping: {
      method: 'ping',
    },
  },
  components: {
    Dialog,
    Card,
    ListItem,
    Button,
    Dropdown,
  },
}
</script>
