# roi-fb

model application as entry test to ROI HUNTER. This apps retrieves basic user data and his/her likes from Facebook. Spring Social Facebook was not used as I didn't find what I wanted there.

It is not perfect but it is still pretty good I would say.

Some oppinions and known issues:
[Missing features]
- Retrieving user and persisting to DB should be optimized. Deleting should be optimized aswell.
- Unit tests are not done, however they should be done. (no time for that, sorry. I can provide code of some tests I wrote some time ago).
- Better javadoc would be fine. With links, prettyprint, highlited stuff etc.

[open for discussion]
- Request for downloading data from facebook is not optimal in my opinion -  I would say that path parameters would be easier to use.
- Deleting user - should pages be deleted at all or only relationship data should be deleted. What if page is also liked by someone else? Best way to do it is IMO to check whether the page is liked by someone and if not, then delete it (that's how it works now). But it may be fine to let pages stored in the DB as a history.
- possibility to use app ID and secret could be fine (depends on use which would be better - via REST or application properties).

