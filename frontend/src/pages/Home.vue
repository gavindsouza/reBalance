<template>
  <div class="max-w-3xl py-12 mx-auto grid gap-4 grid-cols-2">
    <Card title="Important" :subtitle=fetch_subtitle() class="col-span-2" style="background-color: rgb(240 253 244);">
      <ListItem
        v-for="notification in important"
        :key="notification.name"
        v-bind:title="notification.title"
        v-bind:subtitle="notification.text"
      >
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
    </Card>

    <Card title="Updates" class="col-span-2" style="background-color: rgb(238 242 255);">
      <ListItem
        v-for="notification in updates"
        :key="notification.name"
        v-bind:title="notification.title"
        v-bind:subtitle="notification.text"
      >
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
    </Card>

    <Card title="Forums" style="background-color: rgb(253 244 255);">
      <ListItem
        v-for="notification in forums"
        :key="notification.name"
        v-bind:title="notification.title"
        v-bind:subtitle="notification.text"
      >
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
    </Card>

    <Card title="Promotions" style="background-color: rgb(253 242 248);">
      <ListItem
        v-for="notification in promos"
        :key="notification.name"
        v-bind:title="notification.title"
        v-bind:subtitle="notification.text"
      >
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
    </Card>
  </div>
</template>

<script>
import { Dialog, Card, ListItem, Button, Dropdown } from 'frappe-ui';

export default {
  name: 'Home',
  data() {
    return {
      important: null,
      updates: null,
      forums: null,
      promos: null,
      timer: '',
      countDown : 60,
    }
  },
  mounted() {
    this.timer = setInterval(this.fetchNotifications, 60_000);
    this.fetchNotifications();
    this.countDownTimer();
  },
  methods: {
    async fetchNotifications() {
      this.important = await this.$call('rebalance.api.get', {category: 'Important'});
      this.updates = await this.$call('rebalance.api.get', {category: 'Updates'});
      this.forums = await this.$call('rebalance.api.get', {category: 'Forums'});
      this.promos = await this.$call('rebalance.api.get', {category: 'Promotions'});
      this.countDown = 60;
    },
    fetch_subtitle() {
      if (this.countDown == 0) {
        return `Refreshing dashboard...`;
      } else if (this.countDown >= 25) {
        return `Dashboard refreshed`;
      } else {
        return `Last refresh ${ 60 - this.countDown } seconds ago`;
      }
    },
    cancelAutoUpdate () {
        clearInterval(this.timer);
    },
    beforeDestroy () {
      this.cancelAutoUpdate();
    },
    countDownTimer() {
      if (this.countDown > 0) {
          setTimeout(() => {
              this.countDown -= 5
              this.countDownTimer()
          }, 5000)
      }
    }
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
