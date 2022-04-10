# Copyright (c) 2022, Gavin D'souza and contributors
# For license information, please see license.txt

from typing import Dict
import frappe
from frappe.model.document import Document

DEFAULT_CATEGORIES = {
    "Important": ["critical", "asap"],
    "Updates": ["assigned", "requested", "rejected", "test", "email"],
    "Forums": ["upvoted", "mentioned"],
    "Promotions": [
        "deal",
        "congrats",
        "winner",
        "pre-approved credit",
        "offer",
        "sale",
        "off",
    ],
}


def get_composite_category(category_dict: Dict) -> str:
    # TODO: Use a numeric rating system for value comparisons
    _max = 0
    _category = None

    for category, probability in category_dict.items():
        if category and probability > _max:
            _category = category
            _max = probability

    return _category


class PhoneNotification(Document):
    def validate(self):
        self.set_app_name()
        self.update_notification_category()

    def guess_category(self):
        # TODO: Use a system that would be better suited for this (NLP)
        _title = self.title.casefold()
        _text = self.text.casefold()

        for category, keywords in DEFAULT_CATEGORIES.items():
            for keyword in keywords:
                _keyword = keyword.casefold()
                if (_keyword in _title) or (_keyword in _text):
                    return category

    def set_app_name(self):
        self.app = self.app or frappe.db.get_value(
            "Phone Application Package", {"package": self.package}, "app"
        )

    def update_notification_category(self):
        if (not self.is_new() and self.has_value_changed("category")) or self.category:
            return

        app_category = frappe.db.get_value(
            "Phone Application Package",
            {"app": self.app, "package": self.package},
            "category",
        )
        notification_category = self.guess_category()
        self.category = get_composite_category(
            {notification_category: 0.8, app_category: 0.2}
        )
        print(app_category, notification_category, self.category, "CATEGORY")
